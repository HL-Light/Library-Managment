package com.li.librarymanagement.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.li.librarymanagement.common.Result;
import com.li.librarymanagement.entity.Book_c;
import com.li.librarymanagement.service.impl.BookService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.text.DecimalFormat;

import java.io.File;
import java.io.IOException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ExcelController.java
 * @Description TODO
 * @createTime 2023年05月22日 14:04:00
 */

@CrossOrigin
@RestController
@RequestMapping("/excel")
public class ExcelController {
    private final BookService bookService;
    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/bookfiles/";


    @Autowired
    public ExcelController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/upload")
    public Result uploadExcel(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;

        try {
            FileUtil.mkParentDirs(filePath);  // 创建父级目录
            file.transferTo(FileUtil.file(filePath));
            File excelFile = new File(filePath);
            Workbook workbook = WorkbookFactory.create(excelFile);
            Sheet sheet = workbook.getSheetAt(0); // 假设Excel中的数据在第一个Sheet中
            System.out.println(sheet.getLastRowNum());

            // 从第二行开始读取数据
            for (int i = 1; i <=sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    // 跳过空行
                    continue;
                }

                Cell addressCell = row.getCell(0);
                Cell bookNoCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                Cell descriptionCell = row.getCell(3);
                Cell publishDateCell = row.getCell(4);
                Cell authorCell = row.getCell(5);
                Cell publisherCell = row.getCell(6);
                Cell categoryCell = row.getCell(7);



                addressCell.setCellType(CellType.STRING);
                bookNoCell.setCellType(CellType.STRING);
                nameCell.setCellType(CellType.STRING);
                descriptionCell.setCellType(CellType.STRING);
                authorCell.setCellType(CellType.STRING);
                publishDateCell.setCellType(CellType.STRING);
                publisherCell.setCellType(CellType.STRING);
                categoryCell.setCellType(CellType.STRING);

                CellType cellType = descriptionCell.getCellType();
                System.out.println(cellType);

                String adress = addressCell.getStringCellValue();
                String  bookNo = bookNoCell.getStringCellValue();
                String name = nameCell.getStringCellValue();
                String description = descriptionCell.getStringCellValue();
                String  author = authorCell.getStringCellValue();
                String publishDate = publishDateCell.getStringCellValue();
                String publisher = publisherCell.getStringCellValue();
                String category = categoryCell.getStringCellValue();

                Book_c obj = new Book_c();
                obj.setBookNo(bookNo);
                obj.setAdress(adress);
                obj.setName(name);
                obj.setDescription(description);
                obj.setAuthor(author);
                obj.setPublishDate(publishDate);
                obj.setPublisher(publisher);
                obj.setCategory(category);

                bookService.save(obj);
            }

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("400","导入失败");
        }
    }

    private String uploadImage(String imageName, MultipartFile file) {
        try {
            String url = "http://localhost:9090/api/book/file/upload"; // 上传图片的接口URL
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file.getResource());
            body.add("filename", imageName);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Result> response = restTemplate.postForEntity(url, requestEntity, Result.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Result result = response.getBody();
                if (result != null && result.getCode().equals("200")) {
                    return (String) result.getData(); // 假设返回的数据中包含URL字段
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
