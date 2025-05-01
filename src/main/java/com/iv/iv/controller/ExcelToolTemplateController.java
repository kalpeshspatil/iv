package com.iv.iv.controller;

import com.iv.iv.dto.ExcelToolTemplateRequestDto;
import com.iv.iv.entity.Challan;
import com.iv.iv.entity.ExcelImportExportToolTemplate;
import com.iv.iv.service.ExcelToolTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/tools/excel/templates")
@CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
public class ExcelToolTemplateController {

    @Autowired
    ExcelToolTemplateService excelToolTemplateService;

    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
    @PostMapping
    public ResponseEntity<ExcelImportExportToolTemplate> saveTemplate(@RequestBody ExcelToolTemplateRequestDto dto) {
        try {
            return ResponseEntity.ok(excelToolTemplateService.saveExcelImportExportToolTemplate(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
    @GetMapping
    public List<ExcelImportExportToolTemplate> getAllTemplate() {
        return excelToolTemplateService.getAllExcelImportExportToolTemplate();
    }
}
