package ru.otus.spring.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final MutableAclService aclService;
    private final PlatformTransactionManager transactionManager;

    @Transactional
    @Override
    public void addPermissionForUser(Class<?> objectClass,
                                     long objectId,
                                     Permission permission,
                                     String userLogin) {
        Sid sid = new PrincipalSid(userLogin);
        addPermissionForSid(objectClass, objectId, permission, sid);
    }

    @Transactional
    @Override
    public void addPermissionForAuthority(Class<?> objectClass,
                                          long objectId,
                                          Permission permission,
                                          String authority) {
        Sid sid = new GrantedAuthoritySid(authority);
        addPermissionForSid(objectClass, objectId, permission, sid);
    }

    private void addPermissionForSid(Class<?> objectClass,
                                     long objectId,
                                     Permission permission,
                                     Sid sid) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                ObjectIdentity objectIdentity = new ObjectIdentityImpl(objectClass, objectId);
                MutableAcl acl;
                try {
                    acl = (MutableAcl) aclService.readAclById(objectIdentity);
                } catch (NotFoundException e) {
                    acl = aclService.createAcl(objectIdentity);
                }
                acl.insertAce(acl.getEntries()
                        .size(), permission, sid, true);
                aclService.updateAcl(acl);
            }
        });
    }
}
