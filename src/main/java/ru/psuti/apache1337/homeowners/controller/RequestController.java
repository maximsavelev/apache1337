package ru.psuti.apache1337.homeowners.controller;

import ru.psuti.apache1337.homeowners.dto.RequestDto;
import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.service.RequestService;
import ru.psuti.apache1337.homeowners.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(path = RequestController.REQUEST_CONTROLLER_URL)
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    static final String REQUEST_CONTROLLER_URL = "/rest/request";
    private final RequestService requestService;

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<RequestDto>> getAllRequest(
            @RequestParam(value = "startdate", required = false) LocalDateTime startDate,
            @RequestParam(value = "enddate", required = false) LocalDateTime endDate,
            @RequestParam(value = "status", required = false) Long status,
            @RequestParam(value = "clientId", required = false) Long clientId,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "date") String order,
            @AuthenticationPrincipal UserDetails userDetails) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order).descending());
        List<RequestDto> result = requestService.findAll(status,clientId,startDate,endDate,pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public
    ResponseEntity<RequestDto> getRequestById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        RequestDto request = requestService.findRequestById(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<?> createRequest(@RequestBody RequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findUserByPhone(userDetails.getUsername());
            requestDto.setClientId(user.getId());
            requestDto.setStatus(1);
            requestDto.setDate(LocalDateTime.now());
            requestDto.setAddressId(user.getAddress().getId());
            RequestDto result = requestService.save(requestDto);
            log.info("created request: "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //TODO: should be PutMapping
    @PostMapping(path = "{id}")
    public ResponseEntity<?> updateRequest(@PathVariable(name = "id") Long id,
                                           @RequestBody RequestDto requestDto,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            RequestDto request = requestService.findRequestById(id);
            request.setTitle(requestDto.getTitle());
            request.setComment(requestDto.getComment());
            request.setFileName(requestDto.getFileName());
            request.setClientId(userService.findUserByPhone(userDetails.getUsername()).getId());
            RequestDto result = requestService.save(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

   @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
       if(requestService.isRequestExistsById(id)){
           requestService.deleteRequestById(id);
           return ResponseEntity.ok().build();
       }
       else{
           return ResponseEntity.noContent().build();
       }
   }

}
