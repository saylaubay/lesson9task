package uz.pdp.lesson9task1.service;

import uz.pdp.lesson9task1.payload.ApiResponse;

import java.util.UUID;

public interface ProjectUserService {


    ApiResponse addUserToProject(UUID projectId, UUID userId);

    ApiResponse editUser(UUID projectId, UUID userId);

    ApiResponse deleteUserInProject(UUID projectId, UUID userId);

}
