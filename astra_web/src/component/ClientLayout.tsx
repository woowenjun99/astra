"use client";
import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { onAuthStateChanged } from "firebase/auth";
import { auth } from "@/firebase";
import { LoadingOverlay } from "@mantine/core";

export default function ClientLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const router = useRouter();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      if (user === null && pathname !== "/auth") {
        router.push("/auth");
      } else if (user !== null && pathname === "/auth") {
        router.push("/");
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
