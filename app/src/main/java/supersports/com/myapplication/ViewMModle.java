package supersports.com.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

/**
 * Created by rocky on 2019/3/19.
 */

public class ViewMModle extends AndroidViewModel {


    public ViewMModle(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    MutableLiveData<Person> mutableLiveData = new MutableLiveData<>();

    public LiveData<Person> getModelList() {
        Person person=new Person();
        person.setUserName("张三");
        person.setPassword("123");
        mutableLiveData.setValue(person);
        return mutableLiveData;
    }


    class Person {
        private String userName;
        private String password;
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
