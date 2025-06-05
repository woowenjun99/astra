"use client";
import { useEffect } from "react";
import { usePathname, useRouter } from "next/navigation";
import { onAuthStateChanged } from "firebase/auth";
import { auth } from "@/firebase";

export default function ClientLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const router = useRouter();

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      if (user === null && pathname !== "/auth") {
        router.push("/auth");
      } else if (user !== null && pathname === "/auth") {
        router.push("/");
      }
    });

    // Cleanup the listener on unmount
    return () => unsubscribe();
  }, [pathname, router]);

  return <>{children}</>;
}
