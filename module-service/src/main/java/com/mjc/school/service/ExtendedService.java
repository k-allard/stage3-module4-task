package com.mjc.school.service;

import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import com.mjc.school.service.dto.ServiceTagDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;

import java.util.List;

public interface ExtendedService {
    ServiceAuthorResponseDto readAuthorByNewsId(Long id);

    List<ServiceTagDto> readTagsByNewsId(Long id);

    List<ServiceNewsResponseDto> readNewsByParams(List<Long> tagsIds, String tagName, String authorName, String title, String content);
}