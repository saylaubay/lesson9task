package uz.pdp.lesson9task1.service;

import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.entity.Workspace;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.MemberDTO;
import uz.pdp.lesson9task1.payload.WorkspaceDTO;

import java.util.List;
import java.util.UUID;


public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO);

    ApiResponse joinToWorkspace(Long id, User user);

    ApiResponse viewMembers(Long id);

    ApiResponse viewWorkspaces(UUID id);

    ApiResponse viewGuest(Long id);
}
