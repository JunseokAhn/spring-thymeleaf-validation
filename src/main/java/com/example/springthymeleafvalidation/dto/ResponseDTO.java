package com.example.springthymeleafvalidation.dto;

import com.example.springthymeleafvalidation.EnumStatus;

public record ResponseDTO(EnumStatus status, Object data) {
}
