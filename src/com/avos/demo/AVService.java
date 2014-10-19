package com.avos.demo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.avos.avoscloud.*;
import com.avos.avoscloud.search.AVSearchQuery;

import java.util.Collections;
import java.util.List;

/**
 * Created by lzw on 14-9-12.
 */
public class AVService {
  public static void AVInit(Context ctx) {
    // ��ʼ��Ӧ�� Id �� Ӧ�� Key����������Ӧ�����ò˵����ҵ���Щ��Ϣ
    AVOSCloud.initialize(ctx, "8gkau2x0fwj85akuvv11no1dctxw8ljbxtz1kmyxi0mexxj8",
        "mjw0u3ddmq05b3txvpix4jfaumjlqew13hu3vrauy05sj3r4");
    // ���ñ������󱨸�
    AVAnalytics.enableCrashReport(ctx, true);
    // ע������
    AVObject.registerSubclass(Todo.class);
  }

  public static void fetchTodoById(String objectId,GetCallback<AVObject> getCallback) {
    Todo todo = new Todo();
    todo.setObjectId(objectId);
    // ͨ��Fetch��ȡcontent����
    todo.fetchInBackground(getCallback);
  }

  public static void createOrUpdateTodo(String objectId, String content, SaveCallback saveCallback) {
    final Todo todo = new Todo();
    if (!TextUtils.isEmpty(objectId)) {
      // �������objectId��������ɸ��²�����
      todo.setObjectId(objectId);
    }
    todo.setContent(content);
    // �첽����
    todo.saveInBackground(saveCallback);
  }

  public static List<Todo> findTodos() {
    // ��ѯ��ǰTodo�б�
    AVQuery<Todo> query = AVQuery.getQuery(Todo.class);
    // ���ո���ʱ�併������
    query.orderByDescending("updatedAt");
    // ��󷵻�1000��
    query.limit(1000);
    try {
      return query.find();
    } catch (AVException exception) {
      Log.e("tag", "Query todos failed.", exception);
      return Collections.emptyList();
    }
  }

  public static void searchQuery(String inputSearch) {
    AVSearchQuery searchQuery = new AVSearchQuery(inputSearch);
    searchQuery.search();
  }
}