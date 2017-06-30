package com.zzu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.zzu.dao.AdvisoryDao;
import com.zzu.entity.Advisory;

import life.treeHole.common.dao.impl.BaseDaoHibernate5;
@Repository(value="advisoryDaoHibernate5")
public class AdvisoryDaoHibernate5 <T> extends BaseDaoHibernate5<T> implements AdvisoryDao<T> {

} 
 