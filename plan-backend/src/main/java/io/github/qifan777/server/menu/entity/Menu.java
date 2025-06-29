package io.github.qifan777.server.menu.entity;

import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.dict.model.DictConstants.MenuType;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.role.entity.RoleMenuRel;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.*;

import java.util.List;

@GenEntity
@Entity
public interface Menu extends BaseEntity {

    @GenField(value = "菜单名称", order = 0)
    @Key
    String name();

    @OnDissociate(DissociateAction.DELETE)
    @GenField(value = "父菜单Id", order = 1)
    @Null
    @ManyToOne
    Menu parent();

    @GenField(value = "路由路径", order = 2)
    String path();

    @GenField(value = "排序号", order = 3)
    Integer orderNum();

    @GenField(value = "菜单类型", type = ItemType.SELECTABLE, dictEnName = DictConstants.MENU_TYPE, order = 4)
    MenuType menuType();

    @GenField(value = "图标", type = ItemType.PICTURE, order = 5)
    @Null
    String icon();

    @OneToMany(mappedBy = "menu")
    List<RoleMenuRel> roles();


    @GenField(value = "是否可见")
    boolean visible();
}
