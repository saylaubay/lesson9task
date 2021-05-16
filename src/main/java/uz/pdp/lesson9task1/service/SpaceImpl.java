package uz.pdp.lesson9task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import uz.pdp.lesson9task1.entity.*;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.SpaceDto;
import uz.pdp.lesson9task1.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceImpl implements SpaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    IconRepository iconRepository;
    @Autowired
    SpaceUserRepository spaceUserRepository;


    @Override
    public ApiResponse add(SpaceDto spaceDto, User user) {
        boolean exists = spaceRepository.existsByOwnerIdAndNameAndWorkspaceId(user.getId(), spaceDto.getName(), spaceDto.getWorkspaceId());
        if (exists){
            return new ApiResponse("Sizda bunday nomli space mavjud", false);
        }
        Space space = new Space(
                spaceDto.getName(),
                spaceDto.getColor(),
                spaceDto.getWorkspaceId() == null ? null : workspaceRepository.findById(spaceDto.getWorkspaceId()).orElseThrow(() -> new ResourceNotFoundException("workspace")),
                spaceDto.getIconId() == null ? null : iconRepository.findById(spaceDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("icon")),
                spaceDto.getAvatarId() == null ? null : attachmentRepository.findById(spaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("avatar")),
                user,
                true
        );

        spaceRepository.save(space);
        return new ApiResponse("Space saqlandi", true);
    }

    @Override
    public ApiResponse getAllSpace(User user) {
        Optional<List<Space>> optionalSpaces = spaceRepository.findByOwnerId(user.getId());
        if (optionalSpaces.isPresent()){
            List<Space> spaces = optionalSpaces.get();
            return new ApiResponse("Space list", true, spaces);
        }
        return new ApiResponse("Error", false);
    }

    @Override
    public ApiResponse editSpace(Long id, SpaceDto spaceDto) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (!optionalSpace.isPresent()){
            return new ApiResponse("Bunday id li space bazada topilmadi", false);
        }
        Space space = optionalSpace.get();
        space.setColor(spaceDto.getColor());
        space.setName(spaceDto.getName());

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(spaceDto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()){
            return new ApiResponse("Bunday Workspace id topilmadi", false);
        }
        Workspace workspace = optionalWorkspace.get();

        space.setWorkspace(workspace);

        space.setAccessType(true);

        spaceRepository.save(space);

        return new ApiResponse("Space edit qilindi", true);
    }

    @Override
    public ApiResponse deleteSpace(Long id) {
        try {
            spaceRepository.deleteById(id);
            return new ApiResponse("Space o'chirildi", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }


}
