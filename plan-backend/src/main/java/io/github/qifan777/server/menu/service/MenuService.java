package io.github.qifan777.server.menu.service;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.menu.entity.Menu;
import io.github.qifan777.server.menu.entity.dto.MenuInput;
import io.github.qifan777.server.menu.entity.dto.MenuSpec;
import io.github.qifan777.server.menu.repository.MenuRepository;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu findById(String id) {
        return menuRepository.findById(id, MenuRepository.COMPLEX_FETCHER)
                .orElseThrow(() -> new BusinessException(
                        ResultCode.NotFindError, "数据不存在"));
    }

    public String save(MenuInput menuInput) {
        return menuRepository.save(menuInput).id();
    }

    public Page<Menu> query(QueryRequest<MenuSpec> queryRequest) {
        return menuRepository.findPage(queryRequest, MenuRepository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        menuRepository.deleteAllById(ids);
        return true;
    }
}