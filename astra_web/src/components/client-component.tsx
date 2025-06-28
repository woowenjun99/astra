"use client";
import { app, auth } from "@/util/firebase";
import { onAuthStateChanged } from "firebase/auth";
import { redirect, usePathname } from "next/navigation";
import { useEffect, useState, type FC, type ReactNode } from "react";
import { getMessaging, getToken } from "firebase/messaging";
import { env } from "@/env";
import { registerDevice } from "@/services/devices/data/device_repository";

type ClientComponentProps = {
  children: ReactNode;
};

const ClientComponent: FC<ClientComponentProps> = ({ children }) => {
  const pathName = usePathname();
  const [isAuthStateReady, setIsAuthStateReady] = useState(false);

  useEffect(() => {
    async function registerServiceWorker() {
      const messaging = getMessaging(app);

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

    const unsubscribe = onAuthStateChanged(auth, async (user) => {
      if (user !== null) {
        if ("serviceWorker" in navigator && "PushManager" in window) {
          await registerServiceWorker();
        }
        if (pathName === "/auth") redirect("/main");
      } else if (pathName !== "/" && pathName?.startsWith("/docs")) {
        redirect("/main");
      }
      setIsAuthStateReady(true);
    });
    return () => unsubscribe();
  }, [pathName]);

  if (!isAuthStateReady) return <></>;
  return children;
};

export default ClientComponent;
