package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.CommandHandler;
import com.mjc.school.controller.ExtendedController;
import com.mjc.school.controller.dto.AuthorResponseDto;
import com.mjc.school.controller.dto.NewsRequestDto;
import com.mjc.school.controller.dto.NewsResponseDto;
import com.mjc.school.controller.dto.TagDto;
import com.mjc.school.controller.mapper.ServiceToWebDTOMapper;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtendedService;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import com.mjc.school.service.dto.ServiceTagDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class NewsController implements BaseController<NewsRequestDto, NewsResponseDto, Long>, ExtendedController {
    private final BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService;
    private final ExtendedService extendedService;
    private final ServiceToWebDTOMapper mapper = new ServiceToWebDTOMapper();

    public NewsController(
            @Qualifier("newsService")
            BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> newsService,
            ExtendedService extendedService) {
        this.newsService = newsService;
        this.extendedService = extendedService;
    }

    public List<NewsResponseDto> readAll() {
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        for (ServiceNewsResponseDto serviceDto : newsService.readAll()) {
            newsResponseDtoList.add(mapper.mapServiceNewsResponseDto(serviceDto));
        }
        return newsResponseDtoList;
    }

    @GetMapping("/news/{id}")
    public NewsResponseDto readById(@PathVariable Long id) {
        return mapper.mapServiceNewsResponseDto(
                newsService.readById(id));
    }

    @PostMapping("/news")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponseDto create(@RequestBody NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.create(mapper.mapNewsRequestDto(dtoRequest)));
    }

    @PutMapping("/news")
    public NewsResponseDto update(@RequestBody NewsRequestDto dtoRequest) {
        return mapper.mapServiceNewsResponseDto(
                newsService.update(mapper.mapNewsRequestDto(dtoRequest)));
    }

    @DeleteMapping("/news/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id) {
        return newsService.deleteById(id);
    }

    @GetMapping("/news/{id}/author")
    public AuthorResponseDto readAuthorByNewsId(@PathVariable Long id) {
        return mapper.mapServiceAuthorResponseDto(extendedService.readAuthorByNewsId(id));
    }

    @GetMapping("/news/{id}/tags")
    public List<TagDto> readTagsByNewsId(@PathVariable Long id) {
        List<ServiceTagDto> serviceTagDtos = extendedService.readTagsByNewsId(id);
        List<TagDto> tagsList = new ArrayList<>();
        for (ServiceTagDto tag : serviceTagDtos) {
            tagsList.add(mapper.mapServiceTagDto(tag));
        }
        return tagsList;
    }

    @GetMapping(value = "/news")
    public List<NewsResponseDto> readNewsByParams(
            @RequestParam(required = false) List<Long> tagsIds,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content) {
        if (tagsIds == null
                && tagName == null
                && authorName == null
                && title  == null
                && content == null) {
            return readAll();
        }
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        for (ServiceNewsResponseDto serviceDto : extendedService.readNewsByParams(
                tagsIds, tagName, authorName, title, content)) {
            newsResponseDtoList.add(mapper.mapServiceNewsResponseDto(serviceDto));
        }
        return newsResponseDtoList;
    }
}
