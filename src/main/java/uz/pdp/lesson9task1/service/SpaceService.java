package uz.pdp.lesson9task1.service;

import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.SpaceDto;

public interface SpaceService {

    ApiResponse add(SpaceDto spaceDto, User user);

    ApiResponse getAllSpace(User user);

    ApiResponse editSpace(Long id, SpaceDto spaceDto);

    ApiResponse deleteSpace(Long id);

}
