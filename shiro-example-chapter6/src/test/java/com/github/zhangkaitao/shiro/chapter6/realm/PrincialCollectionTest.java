package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import com.github.zhangkaitao.shiro.chapter6.entity.User;
import junit.framework.Assert;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-29
 * <p>Version: 1.0
 */
public class PrincialCollectionTest extends BaseTest {

    @Test
    public void test() {

        //因为Realm里没有进行验证，所以相当于每个Realm都身份验证成功了
        login("classpath:shiro-multirealm.ini", "zhang", "123");
        Subject subject = subject();
        //获取Primary Principal（即第一个）
        Object primaryPrincipal1 = subject.getPrincipal();
        PrincipalCollection princialCollection = subject.getPrincipals();
        Object primaryPrincipal2 = princialCollection.getPrimaryPrincipal();

        System.out.println("primaryPrincipal1="+primaryPrincipal1);
        //但是因为多个Realm都返回了Principal，所以此处到底是哪个是不确定的
        Assert.assertEquals(primaryPrincipal1, primaryPrincipal2);


        //返回 a b c
        Set<String> realmNames = princialCollection.getRealmNames();
        System.out.println("realmNames="+realmNames);

        //MyRealm1和MyRealm2返回的凭据都是zhang，所以排重了
        Set<Object> principals = princialCollection.asSet(); //asList和asSet的结果一样
        System.out.println("set principals="+principals);

        List<Object> principalslist = princialCollection.asList();
        System.out.println("List principals="+principalslist);
        Iterator<Object> iterator = princialCollection.iterator();
        while(iterator.hasNext()){
            Object o = iterator.next();
            System.out.println("o="+o);
        }
        //根据Realm名字获取
        Collection<User> users = princialCollection.fromRealm("c");
        System.out.println("users"+users);
    }
}
