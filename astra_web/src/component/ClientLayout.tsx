"use client";
import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { onAuthStateChanged } from "firebase/auth";
import { app, auth } from "@/firebase";
import { LoadingOverlay } from "@mantine/core";
import { getMessaging, getToken } from "firebase/messaging";
import { addPushNotification } from "@/services/users/data/user-api";
import { env } from "@/env";

export default function ClientLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const router = useRouter();
  const [authReady, setAuthReady] = useState(false);

  async function registerServiceWorker() {
    await navigator.serviceWorker.register("/sw.js", {
      scope: "/",
      updateViaCache: "none",
    });
  }

  async function subscribeUser() {
    const messaging = getMessaging(app);
    const permission = await Notification.requestPermission();

    // If user rejects sending push notification, we do not do anything.
    if (permission === "denied") {
      return;
    }
    const registration = await navigator.serviceWorker.ready;
    const token = await getToken(messaging, {
      serviceWorkerRegistration: registration,
      vapidKey: env.NEXT_PUBLIC_FIREBASE_CLOUD_MESSAGING_VAPID_KEY,
    });
    await addPushNotification({ pushNotificationToken: token });
  }

  useEffect(() => {
    if ("serviceWorker" in navigator && "PushManager" in window) {
      registerServiceWorker();
    }
  }, []);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      // If the user is in the home page, do not need to do anything
      switch (pathname) {
        case "/auth":
          if (user === null) router.push("/auth");
          break;
        case "/":
          break;
        default:
          router.push("/main");
          subscribeUser();
      }
      setAuthReady(true);
    });

    // Cleanup the listener on unmount
    return () => unsubscribe();
  }, [pathname, router]);

  // Firebase Auth takes sometime to be initialised. If we do not render other stuff
  // our JWT token might throw an error.
  if (!authReady) {
    return (
      <div style={{ width: "100vw", height: "100vh" }}>
        <LoadingOverlay />
      </div>
    );
  }

  return <>{children}</>;
}
