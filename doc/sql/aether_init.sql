# 初始化 sys_dict
truncate sys_dict;
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50247825258450944, 'sex', '性别值', '未知', '0', 10, 0, '2022-12-29 15:46:55', '2022-12-29 15:54:32');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50247872180129792, 'sex', '性别值', '男性', '1', 20, 0, '2022-12-29 15:47:06', '2022-12-29 15:54:32');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50247913401749504, 'sex', '性别值', '女性', '2', 30, 0, '2022-12-29 15:47:16', '2022-12-29 15:54:32');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50249611734159360, 'user_type', '用户类型', '内部用户', '0', 10, 0, '2022-12-29 15:54:01', '2022-12-29 15:54:01');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50249797453746176, 'user_type', '用户类型', '注册用户', '1', 20, 0, '2022-12-29 15:54:45', '2022-12-29 15:54:45');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50576573363130368, 'menu_display', '菜单显示状态', '显示', '0', 10, 0, '2022-12-30 13:33:15', '2022-12-30 13:33:15');
INSERT INTO sys_dict (id, dict_type_code, dict_type_name, dict_name, dict_code, dict_sort, delete_at, gmt_create, gmt_modify) VALUES (50576948040306688, 'menu_display', '菜单显示状态', '隐藏', '1', 20, 0, '2022-12-30 13:34:44', '2022-12-30 13:34:44');
# 初始化 sys_param
truncate sys_param;
INSERT INTO sys_param (id, param_type_code, param_type_name, param_name, param_code, param_value, param_sort, delete_at, gmt_create, gmt_modify) VALUES (54215521759137792, null, null, '默认初始密码', 'DEFAULT_PASSWORD', 'abc123456', 10, 0, '2023-01-09 14:33:08', '2023-01-09 14:33:08');
INSERT INTO sys_param (id, param_type_code, param_type_name, param_name, param_code, param_value, param_sort, delete_at, gmt_create, gmt_modify) VALUES (54215782363828224, null, null, '默认令牌过期时间(小时)', 'DEFAULT_TOKEN_EXPIRE_TIME', '6', 20, 0, '2023-01-09 14:34:10', '2023-01-09 14:34:10');
INSERT INTO sys_param (id, param_type_code, param_type_name, param_name, param_code, param_value, param_sort, delete_at, gmt_create, gmt_modify) VALUES (54227247938408448, null, null, '默认用户密码加解密策略', 'DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY', 'md5SaltCrypto', 30, 0, '2023-01-09 15:19:43', '2023-01-09 15:19:43');
# 初始化 sys_role
truncate sys_role;
INSERT INTO sys_role (id, role_code, role_name, role_sort, role_desc, delete_at, gmt_create, gmt_modify) VALUES (49465151442784256, 'admin', '超级管理员', 10, '超级管理员角色', 0, '2022-12-27 11:56:51', '2022-12-27 11:56:51');
INSERT INTO sys_role (id, role_code, role_name, role_sort, role_desc, delete_at, gmt_create, gmt_modify) VALUES (52807568615280640, 'default', '默认角色', 20, '默认角色', 0, '2023-01-05 17:18:25', '2023-01-05 17:18:25');
INSERT INTO sys_role (id, role_code, role_name, role_sort, role_desc, delete_at, gmt_create, gmt_modify) VALUES (52807657781989376, 'student', '学生角色', 30, '学生角色', 0, '2023-01-05 17:18:47', '2023-01-05 17:18:47');
# 初始化 sys_user
truncate sys_user;
INSERT INTO sys_user (id, account, password, nickname, sex, avatar_url, birthday, user_type, enable_status, delete_at, gmt_create, gmt_modify) VALUES (52793893988864000, 'admin', 'baa779b59017ac3ffa8852fb87a2375e', '超级管理员', 0, '', '2023-01-05', 0, 0, 0, '2023-01-05 16:24:05', '2023-01-05 16:24:05');
INSERT INTO sys_user (id, account, password, nickname, sex, avatar_url, birthday, user_type, enable_status, delete_at, gmt_create, gmt_modify) VALUES (52794395153666048, 'guochengqiang', '1ca3219819b76ba4295f5d77dfa94fc4', '一郭菠萝炖不下', 1, '', '1998-11-18', 1, 0, 0, '2023-01-05 16:26:05', '2023-01-05 16:26:05');
