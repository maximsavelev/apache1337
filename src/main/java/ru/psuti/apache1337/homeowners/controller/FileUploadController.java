package ru.psuti.apache1337.homeowners.controller;

import ru.psuti.apache1337.homeowners.security.UserDetailsUserImpl;
import ru.psuti.apache1337.homeowners.service.FileStorageService;
import ru.psuti.apache1337.homeowners.util.Message;
import ru.psuti.apache1337.homeowners.util.AttachmentFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path=FileUploadController.FILE_UPLOAD_CONTROLLER_URL )
@RequiredArgsConstructor
public class FileUploadController {

    static final String FILE_UPLOAD_CONTROLLER_URL = "/rest/files";

    private final FileStorageService fileStorageService;

    @PostConstruct
    private void configureStorage(){
        fileStorageService.clear();
        fileStorageService.init();
    }

    @PostMapping("")
    public ResponseEntity<Message> upload (@RequestParam("file")MultipartFile file,
                                           @AuthenticationPrincipal UserDetailsUserImpl user,
                                           HttpServletRequest request) {
        try {
            String fileName = fileStorageService.save(file);
            return ResponseEntity.ok(new Message(fileName));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Message("Could not upload file " + file.getOriginalFilename()));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<AttachmentFile>> files(@AuthenticationPrincipal UserDetailsUserImpl user) {
        List<AttachmentFile> files = fileStorageService.load()
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileUploadController.class,
                            "getFile",
                            path.getFileName().toString()
                    ).build().toString();
                    return new AttachmentFile(fileName, url);
                }).collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename")String fileName) {
        Resource file = fileStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/init")
    public ResponseEntity<Message> init (@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails.toString().equals("[ADMIN]")) {
                fileStorageService.clear();
                fileStorageService.init();
            }
            return ResponseEntity.ok(new Message("File storage is initailize"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Message("Error initialize file storage"));
        }
    }
}
