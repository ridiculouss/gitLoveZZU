package life.playTogether.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import life.playTogether.dao.ActivityDao;
import life.playTogether.entity.Activity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component(value="activityDao")
public class ActivityDaoImpl implements ActivityDao<Activity>{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	private int id;

	
	public List<Activity> findAll(Class<Activity> entityClazz)
	{
		System.out.println(entityClazz.getSimpleName());
		return (List<Activity>)hibernateTemplate.find("select en from "
			+ entityClazz.getSimpleName() + " en");
		
	}
	
	
	public void save(Activity activity){
		hibernateTemplate.save(activity);
	}
	
	
	public Activity query(int id) {
		return hibernateTemplate.get(Activity.class, id);
	}
	
	
	public void update(Activity activity) {
		hibernateTemplate.update(activity);
	}
	
	
	
	/**
	 * 使用hql 语句进行分页查询操作
	 * @param hql 需要查询的hql语句select * from tabel;
	 * @param pageNo 查询第pageNo页的记录
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	 public List<Activity> findByPage(final String hql,
		final int pageNo, final int pageSize)
	{
		// 通过一个HibernateCallback对象来执行查询
				List<Activity> list = hibernateTemplate
					.execute(new HibernateCallback<List<Activity>>()
					{
						// 实现HibernateCallback接口必须实现的方法
						public List<Activity> doInHibernate1(Session session)
						{
							// 执行Hibernate分页查询
							List<Activity> result = session.createQuery(hql)
								.setFirstResult((pageNo - 1) * pageSize)
								.setMaxResults(pageSize)
								.list();
							return result;
						}

						@Override
						public List<Activity> doInHibernate(Session arg0) throws HibernateException {
							return null;
						}
					});
		return list;
	}
	 
	 
	/**
	 * 使用hql 语句进行分页查询操作
	 * @param hql 需要查询的hql语句
	 * @param pageNo 查询第pageNo页的记录
	 * @param pageSize 每页需要显示的记录数
	 * @param params 如果hql带占位符参数，params用于传入占位符参数
	 * @return 当前页的所有记录
	 */
   public  List<Activity> findByPage(final String hql , final int pageNo, 
		final int pageSize , final  Object... params)
	{
		// 通过一个HibernateCallback对象来执行查询
		List<Activity> list = hibernateTemplate
			.execute(new HibernateCallback<List<Activity>>()
		{
			// 实现HibernateCallback接口必须实现的方法
			public List<Activity> doInHibernate(Session session)
			{
				// 执行Hibernate分页查询
				Query query = session.createQuery(hql);
				// 为包含占位符的HQL语句设置参数
				for(int i = 0 , len = params.length ; i < len ; i++)
				{
					query.setParameter(i + "" , params[i]);
				}
				List<Activity> result = query.setFirstResult((pageNo - 1) * pageSize)
					.setMaxResults(pageSize)
					.list();
				return result;
			}
		});
		return list;
	}


	
	
}
