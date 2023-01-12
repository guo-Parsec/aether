# base相关数据库表结构[mysql]
create table sys_dict
(
    id             bigint                             not null comment '主键'
        primary key,
    dict_type_code varchar(128)                       not null comment '字典类别码值',
    dict_type_name varchar(128)                       not null comment '字典类别名称',
    dict_name      varchar(128)                       not null comment '字典名称',
    dict_code      varchar(128)                       not null comment '字典码值',
    dict_sort      int      default 10                not null comment '字典排序',
    delete_at      bigint   default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modify     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint sys_dict_dict_type_code_dict_code_delete_at_uindex
        unique (dict_type_code, dict_code, delete_at)
)
    comment '系统字典表';

create table sys_menu
(
    id             bigint                                 not null comment '主键'
        primary key,
    parent_id      bigint                                 null comment '上级菜单id',
    absolute_path  varchar(225) default '/'               not null comment '层级路径',
    menu_code      varchar(32)  default ''                not null comment '菜单码值',
    menu_title     varchar(76)  default ''                not null comment '菜单标题',
    menu_icon      varchar(80)  default ''                null comment '菜单图标',
    menu_level     int          default 0                 not null comment '菜单级别',
    menu_sort      int          default 10                not null comment '菜单排序',
    menu_url       varchar(225) default ''                not null comment '菜单路径',
    menu_component varchar(225) default ''                null comment '前端组件',
    menu_display   int          default 0                 not null comment '显示状态',
    menu_desc      varchar(255) default ''                null comment '菜单描述',
    delete_at      bigint       default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modify     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint sys_menu_menu_code_delete_at_uindex
        unique (menu_code, delete_at)
)
    comment '系统菜单表';
create index sys_menu_parent_id_menu_level_index
    on sys_menu (parent_id, menu_level);

create table sys_operate_record
(
    id                bigint          not null comment '主键'
        primary key,
    operate_id        bigint          null comment '操作人id',
    operate_account   varchar(32)     null comment '操作人账户',
    operate_result    int default 0   not null comment '操作结果',
    operate_code      int default 200 not null comment '操作结果码',
    error_reason      longtext        null comment '错误原因',
    operate_ip        varchar(32)     not null comment '操作ip地址',
    operate_time      datetime        not null comment '操作时间',
    time_spent        bigint          not null comment '操作耗时(毫秒)',
    operate_uri       varchar(255)    not null comment '操作uri',
    operate_method    varchar(32)     not null comment '操作类型',
    constraint sys_operate_record_sys_user_account_fk
        foreign key (operate_account) references sys_user (account),
    constraint sys_operate_record_sys_user_id_fk
        foreign key (operate_id) references sys_user (id)
)
    comment '系统操作记录表';

create table sys_param
(
    id              bigint                             not null comment '主键'
        primary key,
    param_type_code varchar(128)                       null comment '参数类别码值',
    param_type_name varchar(128)                       null comment '参数类别名称',
    param_name      varchar(128)                       not null comment '参数名称',
    param_code      varchar(128)                       not null comment '参数码值',
    param_value     varchar(128)                       not null comment '参数值',
    param_sort      int      default 10                not null comment '参数排序',
    delete_at       bigint   default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modify      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint sys_param_param_code_delete_at_uindex
        unique (param_code, delete_at)
)
    comment '系统参数表';

create table sys_resource
(
    id                 bigint                             not null comment '主键'
        primary key,
    resource_type_code varchar(64)                        not null comment '资源类别码值',
    resource_type_name varchar(128)                       not null comment '资源类别名称',
    resource_code      varchar(128)                       not null comment '权限码值',
    resource_name      varchar(128)                       not null comment '权限名称',
    resource_url       varchar(200)                       not null comment '资源路径',
    resource_desc      varchar(255)                       null comment '资源描述',
    delete_at          bigint   default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create         datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modify         datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint sys_resource_resource_code_resource_url_delete_at_uindex
        unique (resource_code, resource_url, delete_at)
)
    comment '系统资源表';

create table sys_role
(
    id         bigint                             not null comment '主键'
        primary key,
    role_code  varchar(128)                       not null comment '角色编码',
    role_name  varchar(128)                       not null comment '角色名称',
    role_sort  int      default 10                not null comment '角色排序',
    role_desc  varchar(255)                       null comment '角色描述信息',
    delete_at  bigint   default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    gmt_modify datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint sys_role_role_code_delete_at_uindex
        unique (role_code, delete_at)
)
    comment '系统角色表';

create table sys_role_menu
(
    role_id bigint not null comment '角色id',
    menu_id bigint not null comment '菜单id',
    primary key (role_id, menu_id),
    constraint sys_role_menu_sys_menu_id_fk
        foreign key (menu_id) references sys_menu (id),
    constraint sys_role_menu_sys_role_id_fk
        foreign key (role_id) references sys_role (id)
)
    comment '角色菜单关联表';

create table sys_role_resource
(
    role_id     bigint not null comment '角色id',
    resource_id bigint not null comment '资源id',
    primary key (role_id, resource_id),
    constraint sys_role_resource_sys_resource_id_fk
        foreign key (resource_id) references sys_resource (id),
    constraint sys_role_resource_sys_role_id_fk
        foreign key (role_id) references sys_role (id)
)
    comment '角色资源关联表';

create table sys_user
(
    id            bigint                                 not null comment '主键'
        primary key,
    account       varchar(32)                            not null comment '登录账户',
    password      varchar(32)                            not null comment '登陆密码',
    nickname      varchar(32)                            null comment '昵称',
    sex           int          default 0                 not null comment '用户性别',
    avatar_url    varchar(128) default ''                null comment '头像地址',
    birthday      date                                   not null comment '出生日期',
    user_type     int          default 0                 not null comment '用户类型',
    enable_status int          default 0                 not null comment '启用状态',
    delete_at     bigint       default 0                 not null comment '数据删除时间(未删除时为0)',
    gmt_create    datetime     default CURRENT_TIMESTAMP null comment '数据创建时间',
    gmt_modify    datetime     default CURRENT_TIMESTAMP null comment '数据修改时间',
    constraint sys_user_account_delete_at_uindex
        unique (account, delete_at)
)
    comment '系统用户表';

create table sys_user_role
(
    user_id bigint not null comment '用户id',
    role_id bigint not null comment '角色id',
    primary key (user_id, role_id),
    constraint sys_user_role_sys_role_id_fk
        foreign key (role_id) references sys_role (id),
    constraint sys_user_role_sys_user_id_fk
        foreign key (user_id) references sys_user (id)
)
    comment '用户角色关联表';