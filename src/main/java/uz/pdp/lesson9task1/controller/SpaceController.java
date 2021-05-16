package uz.pdp.lesson9task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson9task1.entity.User;
import uz.pdp.lesson9task1.payload.ApiResponse;
import uz.pdp.lesson9task1.payload.SpaceDto;
import uz.pdp.lesson9task1.security.CurrentUser;
import uz.pdp.lesson9task1.service.SpaceService;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @PostMapping
    public HttpEntity<?> addSpace(@RequestBody SpaceDto spaceDto, @CurrentUser User user){
        ApiResponse apiResponse=spaceService.add(spaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAllSpace(@CurrentUser User user){
        ApiResponse apiResponse=spaceService.getAllSpace(user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping
    public HttpEntity<?> editSpace(@RequestParam Long id,@RequestBody SpaceDto spaceDto){
        ApiResponse apiResponse=spaceService.editSpace(id, spaceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSpace(@PathVariable Long id){
        ApiResponse apiResponse=spaceService.deleteSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
