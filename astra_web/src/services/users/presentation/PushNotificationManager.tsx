// Source: https://nextjs.org/docs/app/guides/progressive-web-apps#2-implementing-web-push-notifications
"use client";
import { env } from "@/env";
import { app } from "@/firebase";
import { Button } from "@mantine/core";
import { getMessaging, getToken } from "firebase/messaging";
import { useEffect, useState } from "react";
import { addPushNotification } from "../data/user-api";

export default function PushNotificationManager() {
  const [isSupported, setIsSupported] = useState(false);
  const [subscription, setSubscription] = useState<PushSubscription | null>(
    null
  );

  useEffect(() => {
    if ("serviceWorker" in navigator && "PushManager" in window) {
      setIsSupported(true);
      registerServiceWorker();
    }
  }, []);

  async function registerServiceWorker() {
    const registration = await navigator.serviceWorker.register("/sw.js", {
      scope: "/",
      updateViaCache: "none",
    });
    const sub = await registration.pushManager.getSubscription();
    setSubscription(sub);
  }

  async function subscribeUser() {
    const messaging = getMessaging(app);
    const permission = await Notification.requestPermission();
    if (permission === "denied") {
      throw new Error("Please give permission to send push notification");
    }
    const registration = await navigator.serviceWorker.ready;
    // await registration.pushManager.subscribe({
    //   applicationServerKey: env.NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY,
    //   userVisibleOnly: true,
    // });
    const token = await getToken(messaging, {
      serviceWorkerRegistration: registration,
      vapidKey: env.NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY,
    });
    await addPushNotification({ pushNotificationToken: token });
  }

  return (
    <div>
      <Button onClick={subscribeUser}>Subscribe User</Button>
    </div>
  );
}
