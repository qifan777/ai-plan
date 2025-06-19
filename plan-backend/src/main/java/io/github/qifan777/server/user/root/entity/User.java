package io.github.qifan777.server.user.root.entity;

import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseDateTime;
import io.github.qifan777.server.infrastructure.jimmer.UUIDIdGenerator;
import io.github.qifan777.server.role.entity.Role;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.Formula;
import org.babyfish.jimmer.sql.*;

import java.util.List;

@GenEntity
@Entity
public interface User extends BaseDateTime {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    String id();

    @GenField(value = "手机号", order = 0)
    @Key
    String phone();

    @GenField(value = "密码", order = 1)
    String password();

    @GenField(value = "昵称", order = 2)
    @Null
    String nickname();

    @GenField(value = "头像", order = 3, type = ItemType.PICTURE)
    @Null
    String avatar();

    @GenField(value = "性别", order = 4, type = ItemType.SELECTABLE, dictEnName = DictConstants.GENDER)
    @Null
    DictConstants.Gender gender();


    @OneToMany(mappedBy = "user")
    List<UserRoleRel> roles();

    @ManyToManyView(
            prop = "roles",
            deeperProp = "role"
    )
    List<Role> rolesView();

    DictConstants.UserStatus status();
    @Formula(sql = """
            (select count(1) from sign t where t.creator_id=%alias.id)
            """)
    int signCount();
}
