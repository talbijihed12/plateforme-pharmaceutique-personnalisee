package com.project.pppreclamationmicroservice.Controllers;


import com.project.pppreclamationmicroservice.Entity.Attachment;
import com.project.pppreclamationmicroservice.Responses.MessageResponse;
import com.project.pppreclamationmicroservice.services.IServiceAttachment;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/upload")
public class AttachmentController {

    private IServiceAttachment iServiceAttachment;

    //admin
    @GetMapping("/findByClaim/{id}")
    public List<Attachment> findByClaim(@PathVariable Long id) {
        return iServiceAttachment.findByClaim(id);
    }


    //student ou teacher
    @PostMapping("/uploadFile/{id}")
    public ResponseEntity<MessageResponse>  upload(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) {
        MessageResponse message =    iServiceAttachment.uploadFile(files,id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    //admin student and teacher
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = iServiceAttachment.download(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }




}