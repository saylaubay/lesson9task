package uz.pdp.lesson9task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.lesson9task1.entity.*;
import uz.pdp.lesson9task1.entity.enums.AddType;
import uz.pdp.lesson9task1.entity.enums.WorkspacePermissionName;
import uz.pdp.lesson9task1.entity.enums.WorkspaceRoleName;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.MemberDTO;
import uz.pdp.lesson9task1.payload.WorkspaceDTO;
import uz.pdp.lesson9task1.repository.*;
import uz.pdp.lesson9task1.security.CurrentUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user) {
        //WORKSPACE OCHDIK
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDTO.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);

        //WORKSPACE ROLE OCHDIK
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //OWERGA HUQUQLARNI BERYAPAMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //WORKSPACE USER OCHDIK
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        ));

        return new ApiResponse("Ishxona saqlandi", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isPresent()){
            Workspace workspace = optionalWorkspace.get();
            workspace.setColor(workspaceDTO.getColor());
            workspace.setName(workspaceDTO.getName());
            workspaceRepository.save(workspace);
            return new ApiResponse("Workspace edit qilindi", true);
        }
        return new ApiResponse("Bunday id li workspace topilmadi", false);
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
//        System.out.println(optionalWorkspace.get().getOwner());
        if (optionalWorkspace.isPresent()){
            Workspace workspace = optionalWorkspace.get();

            Optional<User> optionalUser = userRepository.findById(ownerId);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();

                workspace.setOwner(user);
                workspaceRepository.save(workspace);
                return new ApiResponse("Success", true);
            }
            return new ApiResponse("Owner id topilmadi", false);
        }
        return new ApiResponse("Workspace id topilmadi", false);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO) {
        if (memberDTO.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(memberDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepository.save(workspaceUser);

            //TODO EMAILGA INVITE XABAR YUBORISH
        } else if (memberDTO.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDTO.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDTO.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDTO.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }

    @Override
    public ApiResponse viewMembers(Long id) {
        List<WorkspaceUser> workspaceUsers = new ArrayList<>();
        Optional<List<WorkspaceUser>> optionalWorkspaceUsers = workspaceUserRepository.findByWorkspaceIdAndWorkspaceRole_Name(id, WorkspaceRoleName.ROLE_MEMBER.name());
        if (optionalWorkspaceUsers.isPresent()){
            workspaceUsers = optionalWorkspaceUsers.get();

            return new ApiResponse("Success", true, optionalWorkspaceUsers.get());
        }
        return new ApiResponse("Error", false);
    }

    @Override
    public ApiResponse viewWorkspaces(UUID id) {
        Optional<List<Workspace>> optionalWorkspaces = workspaceRepository.findByOwnerId(id);
        if (optionalWorkspaces.isPresent()){
            List<Workspace> workspaces = optionalWorkspaces.get();
            System.out.println(workspaces);
            return new ApiResponse("Workspace list", true, workspaces);
        }
        return new ApiResponse("Error", false);
    }


    @Override
    public ApiResponse viewGuest(Long id) {
        List<WorkspaceUser> workspaceUsers = new ArrayList<>();
        Optional<List<WorkspaceUser>> optionalWorkspaceUsers = workspaceUserRepository.findByWorkspaceIdAndWorkspaceRole_Name(id, WorkspaceRoleName.ROLE_GUEST.name());
        if (optionalWorkspaceUsers.isPresent()){
            workspaceUsers = optionalWorkspaceUsers.get();

            return new ApiResponse("Success", true, optionalWorkspaceUsers.get());
        }
        return new ApiResponse("Error", false);
    }
}