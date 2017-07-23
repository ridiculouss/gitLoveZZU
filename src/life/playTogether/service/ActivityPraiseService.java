package life.playTogether.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.playTogether.dao.ActivityDao;
import life.playTogether.entity.Activity;

@Component("activityPraiseService")
@Transactional
public class ActivityPraiseService {
	
	@Resource(name="activityDao")
	private ActivityDao<Activity> activityDao;
	
	public boolean addPraise(int id) {
		Activity activity = activityDao.query(id);
		if (activity==null) {
			return false;
		}
		int oldID = activity.getA_praiseNumber();
		activity.setA_praiseNumber(oldID+1);
		activityDao.update(activity);
		return true;
	}
}
