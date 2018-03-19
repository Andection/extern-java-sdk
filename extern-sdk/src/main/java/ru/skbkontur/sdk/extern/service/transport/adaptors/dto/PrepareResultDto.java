/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern.service.transport.adaptors.dto;

import java.util.stream.Collectors;

/**
 *
 * @author AlexS
 */
public class PrepareResultDto {
	
	public PrepareResultDto() {
		
	}
	
	public ru.skbkontur.sdk.extern.model.PrepareResult fromDto(ru.skbkontur.sdk.extern.service.transport.swagger.model.PrepareResult dto) {
		ru.skbkontur.sdk.extern.model.PrepareResult prepareResult = null;
		if (dto != null) {
			CheckResultDataDto checkResultDataDto = new CheckResultDataDto();
			LinkDto linkDto = new LinkDto();
			prepareResult = new ru.skbkontur.sdk.extern.model.PrepareResult();
			prepareResult.setCheckResult(checkResultDataDto.fromDto(dto.getCheckResult()));
			prepareResult.setLinks(dto.getLinks().stream().map(l->linkDto.fromDto(l)).collect(Collectors.toList()));
			prepareResult.setStatus(ru.skbkontur.sdk.extern.model.PrepareResult.Status.fromValue(dto.getStatus().getValue()));
		}
		return prepareResult;
	}

	public ru.skbkontur.sdk.extern.service.transport.swagger.model.PrepareResult toDto(ru.skbkontur.sdk.extern.model.PrepareResult prepareResult) {
		ru.skbkontur.sdk.extern.service.transport.swagger.model.PrepareResult dto = null;
		if (dto != null) {
			CheckResultDataDto checkResultDataDto = new CheckResultDataDto();
			LinkDto linkDto = new LinkDto();
			dto = new ru.skbkontur.sdk.extern.service.transport.swagger.model.PrepareResult();
			dto.setCheckResult(checkResultDataDto.toDto(prepareResult.getCheckResult()));
			dto.setLinks(prepareResult.getLinks().stream().map(l->linkDto.toDto(l)).collect(Collectors.toList()));
			dto.setStatus(ru.skbkontur.sdk.extern.service.transport.swagger.model.PrepareResult.StatusEnum.fromValue(prepareResult.getStatus().getValue()));
		}
		return dto;
	}
}