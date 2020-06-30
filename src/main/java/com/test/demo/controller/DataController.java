package com.test.demo.controller;

import com.test.demo.domain.Record;
import com.test.demo.repo.RecordRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/")
public class DataController {
    private final RecordRepo recordRepo;

    @Autowired
    public DataController(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }


    @GetMapping
    public List<Record> getData() {
        return recordRepo.findAll();
    }

    @GetMapping("{id}")
    public Record getOneRecord(@PathVariable("id") Record record) {
        return record;
    }

    @GetMapping("/find")
    public Page<Record> getOneRecord(@RequestParam("from") String from, @RequestParam("to") String to,
                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return recordRepo.findAllByUpdatedIsBetween(new Timestamp(Long.valueOf(from)*1000),new Timestamp(Long.valueOf(to)*1000), pageable);
    }

    @PostMapping
    public Record create(@RequestBody Record record) {
        return recordRepo.save(record);
    }
    @PostMapping("/fileUpload")
    public List<Record> getUploadFrom(Model model,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        List<Record> recordsList = new ArrayList<>();
        if (file != null) {
            InputStream inputStream =  new BufferedInputStream(file.getInputStream());
            Scanner myReader = new Scanner(inputStream);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(!data.equals("") && !data.contains("PRIMARY_KEY")) {
                    System.out.println(data);
                    String[] dataarray = data.split(",");
                    recordsList.add(new Record(dataarray));
                }

            }
            myReader.close();
        }

        return recordRepo.saveAll(recordsList);
    }

    @PutMapping("{id}")
    public Record update(@PathVariable("id") Record recordFromDb,
                         @RequestBody Record record) {
        BeanUtils.copyProperties(record, recordFromDb, "id", "primaryKey");
        return recordRepo.save(recordFromDb);
    }

    @DeleteMapping("byid/{id}")
    public void deleteById(@PathVariable("id") Record record) {
        recordRepo.delete(record);

    }

    @DeleteMapping("bypk/{primaryKey}")
    public void deleteByPk(@PathVariable("primaryKey") String primaryKey) {
        Record record = recordRepo.findByPrimaryKey(primaryKey);
        recordRepo.delete(record);

    }

}
