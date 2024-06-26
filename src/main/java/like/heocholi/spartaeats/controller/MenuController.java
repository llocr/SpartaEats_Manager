package like.heocholi.spartaeats.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.MenuResponseDto;
import like.heocholi.spartaeats.dto.MenuUpdateRequestDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.MenuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    // R
    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage<MenuResponseDto>> getMenu(@PathVariable Long storeId, @PathVariable Long menuId,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MenuResponseDto responseDto = menuService.getMenu(storeId,menuId,userDetails.getManager());

        ResponseMessage<MenuResponseDto> responseMessage = ResponseMessage.<MenuResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getName()+"] 메뉴 조회가 완료되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage<List<MenuResponseDto>>> getMenus(@PathVariable Long storeId,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MenuResponseDto> menuResponseDtoList = menuService.getMenus(storeId,userDetails.getManager());

        ResponseMessage<List<MenuResponseDto>> responseMessage = ResponseMessage.<List<MenuResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("["+menuResponseDtoList.get(0).getStoreName() +"]의 모든 메뉴 조회가 완료되었습니다.")
                .data(menuResponseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // C
    // 메뉴추가
    @PostMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage<MenuResponseDto>> addMenu(@PathVariable Long storeId, @RequestBody MenuAddRequestDto requestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto responseDto = menuService.addMenu(storeId, requestDto, userDetails.getManager());

        ResponseMessage<MenuResponseDto> responseMessage = ResponseMessage.<MenuResponseDto>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getName()+"] 메뉴가 추가되었습니다.")
                        .data(responseDto)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // U
    // 메뉴 수정
    @PutMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage<MenuResponseDto>> updateMenu(@PathVariable Long storeId,@PathVariable Long menuId,@RequestBody MenuUpdateRequestDto requestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto responseDto = menuService.updateMenu(storeId,menuId,requestDto,userDetails.getManager());

        ResponseMessage<MenuResponseDto> responseMessage = ResponseMessage.<MenuResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("["+responseDto.getStoreName() + "]에 [" + responseDto.getName()+ "] 메뉴가 수정되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // D
    @DeleteMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage<Long>> deleteMenu(@PathVariable Long storeId,@PathVariable Long menuId,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MenuResponseDto deleteMenu = menuService.deleteMenu(storeId,menuId,userDetails.getManager());

        ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
                .statusCode(HttpStatus.OK.value())
                .message("[" + deleteMenu.getName() + "](이)가 삭제되었습니다.")
                .data(deleteMenu.getId())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }










}
