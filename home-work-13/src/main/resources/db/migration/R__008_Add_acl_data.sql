insert into acl_sid (principal, sid)
values (1, 'ivan1'),
       (1, 'anton1'),
       (0, 'ROLE_MANAGER'),
       (0, 'ROLE_CEO'),
       (0, 'ROLE_CREATOR');

insert into acl_class (class)
values ('ru.otus.spring.model.Book');

insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values (1, 1, NULL, 5, 0),
       (1, 2, NULL, 5, 0);

insert into acl_entry (acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure)
values (1, 1, 1, 1, 1, 1, 1),
       (1, 2, 1, 2, 1, 1, 1),
       (1, 3, 3, 1, 1, 1, 1),
       (1, 4, 3, 2, 1, 1, 1),
       (2, 1, 2, 1, 1, 1, 1),
       (2, 2, 2, 2, 1, 1, 1),
       (2, 3, 4, 1, 1, 1, 1),
       (2, 4, 4, 2, 1, 1, 1);