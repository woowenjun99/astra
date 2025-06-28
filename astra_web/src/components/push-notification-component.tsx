"use client";
import { useEffect, type FC, type ReactNode } from "react";
import { getMessaging, getToken } from "firebase/messaging";
import { app } from "@/util/firebase";
import { env } from "@/env";
import { registerDevice } from "@/services/devices/data/device_repository";

type PushNotificationProps = {
  children: ReactNode;
};

const PushNotificationComponent: FC<PushNotificationProps> = ({ children }) => {
  const messaging = getMessaging(app);

  useEffect(() => {
    if ("serviceWorker" in navigator && "PushManager" in window) {
      registerServiceWorker();
    }
  }, []);

  async function registerServiceWorker() {
    const permission = await window.Notification.requestPermission();

    if (permission !== "granted") {
      return;
    }

    const registration = await navigator.serviceWorker.register("/sw.js", {
      scope: "/",
      updateViaCache: "none",
    });
    const token = await getToken(messaging, {
      serviceWorkerRegistration: registration,
      vapidKey: env.NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY,
    });
    await registerDevice(token);
  }

  return children;
};

export default PushNotificationComponent;
