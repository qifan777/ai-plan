package io.github.qifan777.server.role.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.role.entity.Role;
import io.github.qifan777.server.role.entity.RoleTable;
import io.github.qifan777.server.role.entity.dto.RoleInput;
import io.github.qifan777.server.role.entity.dto.RoleSpec;
import io.github.qifan777.server.role.repository.RoleRepository;
import io.github.qifan777.server.role.service.RoleService;
import io.github.qifan777.server.user.root.entity.UserRoleRelTable;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@AllArgsConstructor
@DefaultFetcherOwner(RoleRepository.class)
public class RoleController {

    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "ROLE_MENU_FETCHER") Role findById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER") Role> query(
            @RequestBody QueryRequest<RoleSpec> queryRequest) {
        return roleService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated RoleInput role) {
        return roleService.save(role);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody List<String> ids) {
        return roleService.delete(ids);
    }

    @GetMapping("list")
    public List<@FetchBy(value = "COMPLEX_FETCHER") Role> list() {
        RoleTable t = RoleTable.$;
        UserRoleRelTable t2 = UserRoleRelTable.$;
        return roleRepository.sql().createQuery(t)
                .where(t.id().in(roleRepository.sql().createSubQuery(t2)
                        .where(t2.userId().eq(StpUtil.getLoginIdAsString()))
                        .select(t2.roleId())))
                .select(t)
                .execute();
    }
}