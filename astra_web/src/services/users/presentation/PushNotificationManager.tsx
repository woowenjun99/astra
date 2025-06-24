// Source: https://nextjs.org/docs/app/guides/progressive-web-apps#2-implementing-web-push-notifications
"use client";
import { env } from "@/env";
import { app } from "@/firebase";
import { Button } from "@mantine/core";
import { getMessaging, getToken } from "firebase/messaging";
import { useEffect } from "react";
import { addPushNotification } from "../data/user-api";
import { IconBell } from "@tabler/icons-react";

export default function PushNotificationManager() {
  useEffect(() => {
    if ("serviceWorker" in navigator && "PushManager" in window) {
      registerServiceWorker();
    }
  }, []);

  async function registerServiceWorker() {
    await navigator.serviceWorker.register("/sw.js", {
      scope: "/",
      updateViaCache: "none",
    });
  }

  async function subscribeUser() {
    const messaging = getMessaging(app);
    const permission = await Notification.requestPermission();
    if (permission === "denied") {
      throw new Error("Please give permission to send push notification");
    }
    const registration = await navigator.serviceWorker.ready;
    const token = await getToken(messaging, {
      serviceWorkerRegistration: registration,
      vapidKey: env.NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY,
    });
    await addPushNotification({ pushNotificationToken: token });
  }

  return (
    <Button onClick={subscribeUser} color="black" leftSection={<IconBell />}>
      Subscribe To Push Notification
    </Button>
  );
}
