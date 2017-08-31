package zzu.themb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import zzu.util.GetDate;

public class Delmap implements ServletContextListener {
	MyThread m = new MyThread();
	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("����������");
		// �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��
		service.scheduleAtFixedRate(m, 10, 1800, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("�������ر�ֹͣ��ʱɾ��mapֵ����");
		if(service!=null)
		service.shutdown();
	}

}

class MyThread implements Runnable {
	@Override
	public void run() {
      System.out.println("��ʱɾ��mapֵ����ִ��");
     
      HashMap<String, List<ThembModuel>> map=  ThembRecord.getmap();
      if(!map.isEmpty()){
      Iterator<String> it=map.keySet().iterator();
      while(it.hasNext()){
    	  List<ThembModuel> newValueList=new ArrayList<ThembModuel>();
    	  String key=(String) it.next();
    	  List<ThembModuel> m=map.get(key);
    	  for (ThembModuel thembModuel : m) {
			try {
				if(GetDate.getmin(thembModuel.getDate(),GetDate.GetNowDate())<30){//������Ӳ�
					newValueList.add(thembModuel);
					System.out.println("�ҵ����ڼ�¼");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		map.put(key, newValueList);	
		}
      }
      }else{System.out.println("map���޼�¼Ϊ��");}
	}
}