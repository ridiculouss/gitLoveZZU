package zzu.util;

public class Judge_character {

	// ����Ƿ������ֲ���Ϊ11λ
	public boolean character(String str) {

		if (str != null && str.matches("[0-9]+") && str.length() == 11) {

			return true;
		} else {
			return false;
		}

	}

	// �жϵ�һ���������Ƿ��ڵڶ������������
	public static boolean JudgeMember(String userId, String[] existeduid) {
		
		for (String s : existeduid) {// �ж��Ƿ��Ѵ���
			if (userId.equals(s)) {
				
				return true;//�����򷵻�true
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Judge_character j = new Judge_character();
		boolean b = j.character("12345678901");
		System.out.println(b);

		System.out.println((int) ((Math.random() * 9 + 1) * 100000000)); // �������9λ��
	}
}
