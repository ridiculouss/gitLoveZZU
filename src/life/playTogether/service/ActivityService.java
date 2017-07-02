package life.playTogether.service;

import java.util.List;

import javax.annotation.Resource;

import life.playTogether.dao.ActivityDao;
import life.playTogether.entity.Activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value="activityService")
public class ActivityService {

	@Resource(name="activityDao")
	private ActivityDao activityDao;
	
	public List<Activity> findAll(){
		return activityDao.findAll(Activity.class);
	}
	public void save(Activity activity){
		activityDao.save(activity);
	}
	public List<Activity> findByPage(final int pageNo, final int pageSize ){
		return activityDao.findByPage("select en from Activity en", pageNo, pageSize);
	}
}
