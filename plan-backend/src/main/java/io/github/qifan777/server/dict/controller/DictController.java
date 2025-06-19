package io.github.qifan777.server.dict.controller;

import io.github.qifan777.server.Immutables;
import io.github.qifan777.server.dict.entity.Dict;
import io.github.qifan777.server.dict.entity.dto.DictInput;
import io.github.qifan777.server.dict.entity.dto.DictSpec;
import io.github.qifan777.server.dict.repository.DictRepository;
import io.github.qifan777.server.dict.service.DictService;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("dict")
@AllArgsConstructor
public class DictController {

    private final DictService dictService;
    private final DictRepository dictRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = DictRepository.class) Dict findById(
            @PathVariable String id) {
        return dictService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = DictRepository.class) Dict> query(
            @RequestBody QueryRequest<DictSpec> queryRequest) {
        return dictService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated DictInput dict) {
        return dictService.save(dict);
    }

    @PostMapping("save/batch")
    public void batchSave(@RequestBody BatchSaveInput input) {
        for (int i = 0; i < input.getDictInputs().size(); i++) {
            var item = input.getDictInputs().get(i);
            int finalI = i;
            dictRepository.save(Immutables.createDict(draft -> {
                draft.setDictId(input.getDictId())
                        .setDictEnName(input.getDictEnName())
                        .setDictName(input.getDictName())
                        .setKeyEnName(item.getKeyEnName())
                        .setKeyId(finalI)
                        .setKeyName(item.getKeyName())
                        .setOrderNum(finalI);
            }));
        }
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody List<String> ids) {
        return dictService.delete(ids);
    }

    @GetMapping("ts")
    public byte[] generateTS() {
        return dictService.generateTS().getBytes(StandardCharsets.UTF_8);
    }

    @GetMapping("java")
    public void generateJava() {
        dictService.generateJava();
    }

    @Data
    public static class BatchSaveInput {
        List<KeyInput> dictInputs;
        private String dictName;
        private String dictEnName;
        private int dictId;

        @Data
        public static class KeyInput {
            private String keyName;
            private String keyEnName;
        }
    }
}