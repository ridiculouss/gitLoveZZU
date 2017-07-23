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
	 * ʹ��hql �����з�ҳ��ѯ����
	 * @param hql ��Ҫ��ѯ��hql���select * from tabel;
	 * @param pageNo ��ѯ��pageNoҳ�ļ�¼
	 * @param pageSize ÿҳ��Ҫ��ʾ�ļ�¼��
	 * @return ��ǰҳ�����м�¼
	 */
	 public List<Activity> findByPage(final String hql,
		final int pageNo, final int pageSize)
	{
		// ͨ��һ��HibernateCallback������ִ�в�ѯ
				List<Activity> list = hibernateTemplate
					.execute(new HibernateCallback<List<Activity>>()
					{
						// ʵ��HibernateCallback�ӿڱ���ʵ�ֵķ���
						public List<Activity> doInHibernate1(Session session)
						{
							// ִ��Hibernate��ҳ��ѯ
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
	 * ʹ��hql �����з�ҳ��ѯ����
	 * @param hql ��Ҫ��ѯ��hql���
	 * @param pageNo ��ѯ��pageNoҳ�ļ�¼
	 * @param pageSize ÿҳ��Ҫ��ʾ�ļ�¼��
	 * @param params ���hql��ռλ��������params���ڴ���ռλ������
	 * @return ��ǰҳ�����м�¼
	 */
   public  List<Activity> findByPage(final String hql , final int pageNo, 
		final int pageSize , final  Object... params)
	{
		// ͨ��һ��HibernateCallback������ִ�в�ѯ
		List<Activity> list = hibernateTemplate
			.execute(new HibernateCallback<List<Activity>>()
		{
			// ʵ��HibernateCallback�ӿڱ���ʵ�ֵķ���
			public List<Activity> doInHibernate(Session session)
			{
				// ִ��Hibernate��ҳ��ѯ
				Query query = session.createQuery(hql);
				// Ϊ����ռλ����HQL������ò���
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
