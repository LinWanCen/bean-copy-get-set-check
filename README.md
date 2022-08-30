# Bean copy get set check 字段拷贝赋值检查

运行 [BeanCopyCheckToExcel.java](src/main/java/io/github/linwancen/bean/check/copy/BeanCopyCheckToExcel.java)
选择文件即可生成 Excel

- copy 表记录了 copyProperties 方法的两个类字段多出来和缺失情况（单向，单层）
  - fromMethod info 列表示源有 get 目标没 set，可能匹配 toMethod warn 或 大 DO 本来就多的。
  - toMethod warn 列表示源没 get 目标有 set，可能导致后面取不到值。
- other 表记录了 getset 方法与字段名不匹配的情况
- 使用正则表达式解析，大项目执行时间1分钟左右。

附：Bean copy 框架性能
```
Cglib BeanCopier > PropertyUtils > Spring BeanUtils > Apache BeanUtils
```

## 代码扫描 SonarCloud

| 指标   | 徽章                                                                                                                                                                                                                |
|--------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 安全   | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=security_rating)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check)       |
| 可维护 | [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check)   |
| 可靠性 | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check) |
| 错误   | [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=bugs)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check)                             |
| 漏洞   | [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check)       |
| 代码行 | [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=LinWanCen_bean-copy-get-set-check&metric=ncloc)](https://sonarcloud.io/dashboard?id=LinWanCen_bean-copy-get-set-check)                   |
