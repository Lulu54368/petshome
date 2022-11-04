package com.itproject.petshome.controller;

import com.itproject.petshome.dto.ImageCollectionDTO;
import com.itproject.petshome.dto.input.ImageInput;
import com.itproject.petshome.dto.output.ImageOutputDTO;
import com.itproject.petshome.exception.PetNotFound;
import com.itproject.petshome.model.Image;
import com.itproject.petshome.model.enums.SetToCover;
import com.itproject.petshome.service.PetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("localhost:3000")
@SecurityRequirement(name="bearerAuth")
@Controller
@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
@Tag(name = "image")
public class ImageController {
    PetService petService;
    @GetMapping("/")
    public List<ImageOutputDTO> getImage(@RequestParam Long petId) throws PetNotFound {
        return petService.getImageCollectionByPet(petId);
    }
    @PostMapping("/")
    public List<ImageOutputDTO> postImage(@RequestParam Long petId,
                                          @RequestBody ImageInput imageInput) throws PetNotFound {
        return petService.postImage(petId, imageInput);
    }
}
