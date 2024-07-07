import React, { useEffect, useState } from "react";
import axios from "axios";
import { authApi, endpoints } from "@/configs/APIs";
import { multiFormatDateString } from "@/lib/utils";
import { Link } from "react-router-dom";

type Notification = {
  id: number;
  userId: string;
  userImage: string;
  userName: string;
  postId: string;
  postTitle: string;
  notificationContent: string;
  createdDate: string;
  isRead: boolean;
};

const NotificationList: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const res = await authApi().get(endpoints["get-notifications"]);

        const convertedNotifications = res.data.map((noti: any) => ({
          id: noti.notificationId,
          userId: noti.postId.userId.id.toString(),
          userImage: noti.postId.userId.avatar,
          userName: noti.postId.userId.name,
          postId: noti.postId.postId.toString(),
          postTitle: noti.postId.title,
          notificationContent: noti.message,
          createdDate: noti.createdDate,
          isRead: noti.isRead,
        }));

        setNotifications(convertedNotifications);
      } catch (error) {
        console.error("Error fetching notifications:", error);
      }
    };

    fetchNotifications();
  }, []);

  return (
    <div className="flex-col w-full">
      <div className="max-w-3xl mx-auto mt-8 min-w-10 ">
        {notifications &&
          notifications.map((notification) => (
            <div
              key={notification.id}
              className={
                notification.isRead
                  ? "bg-gray-900  p-4 mb-4 rounded-lg shadow-md"
                  : "bg-gray-800  p-4 mb-4 rounded-lg shadow-md"
              }
            >
              <div className="flex items-center mb-2">
                <Link to={`/profile/${notification.userId}`} className="flex">
                  <img
                    src={notification.userImage}
                    alt={`${notification.userName}'s avatar`}
                    className="w-10 h-10 rounded-full mr-3"
                  />
                  <div>
                    <p className="text-sm font-medium text-white">
                      {notification.userName}
                    </p>
                    <p className="text-xs text-gray-400">
                      {multiFormatDateString(notification.createdDate)}
                    </p>
                  </div>
                </Link>
              </div>
              <div className="bg-gray-900 p-3 rounded-lg shadow-sm">
                <Link to={`/posts/${notification.userId}`}>
                  <p className="text-base font-bold text-white">
                    {notification.postTitle}
                  </p>
                  <p className="text-sm text-gray-300">
                    {notification.notificationContent}
                  </p>
                </Link>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default NotificationList;
