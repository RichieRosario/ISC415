package dao;

import modelo.Notification;

import java.util.List;

public interface NotificationDao extends IRepositorio<Notification, Integer> {

    @Override
    void add(Notification notification);

    @Override
    Notification findOne(Integer id);

    @Override
    List<Notification> getAll();

    @Override
    void update(Notification notification);

    @Override
    void deleteById(Notification notification);

}
