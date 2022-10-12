create table if not exists acl_sid
(
    id bigserial primary key,
    principal tinyint not null,
    sid varchar(100) not null
    );

alter table acl_sid
    add constraint UK_AclSid_Sid_Principal
        unique (sid, principal);

create table if not exists acl_class
(
    id    bigserial primary key,
    class varchar(255) not null
    );

alter table acl_class
    add constraint UK_AclClass_Class
        unique (class);

create table if not exists acl_entry
(
    id                  bigserial primary key,
    acl_object_identity bigint     not null,
    ace_order           int        not null,
    sid                 bigint     not null,
    mask                int        not null,
    granting            tinyint not null,
    audit_success       tinyint not null,
    audit_failure       tinyint not null
    );

alter table acl_entry
    add constraint UK_AclEntry_AclObjectIdentity_AceOrder
        unique (acl_object_identity, ace_order);

alter table acl_entry
    add constraint FK_AclEntry_Sid
        foreign key (sid) references acl_sid (id);

create table if not exists acl_object_identity
(
    id                 bigserial primary key,
    object_id_class    bigint     not null,
    object_id_identity bigint     not null,
    parent_object      bigint default null,
    owner_sid          bigint default null,
    entries_inheriting tinyint not null
    );

alter table acl_object_identity
    add constraint UK_AclEntry_ObjectIdClass_ObjectIdIdentity
        unique (object_id_class, object_id_identity);

alter table acl_object_identity
    add constraint FK_AclObjectIdentity_ParentObject
        foreign key (parent_object) references acl_object_identity (id);

alter table acl_object_identity
    add constraint FK_AclObjectIdentity_ObjectIdClass
        foreign key (object_id_class) references acl_class (id);

alter table acl_object_identity
    add constraint FK_AclObjectIdentity_OwnerSid
        foreign key (owner_sid) references acl_sid (id);

alter table acl_entry
    add constraint FK_AclEntry_AclObjectIdentity
        foreign key (acl_object_identity) references acl_object_identity (id);