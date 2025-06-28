self.addEventListener("push", function (event) {
  if (event.data) {
    const data = event.data.json();
    const title = data.notification?.title || "No Title";
    const body = data.notification?.body || "No Body";
    const options = {
      body,
      icon: data.icon || "/android-chrome-512x512.png",
      badge: "/android-chrome-512x512.png",
      vibrate: [100, 50, 100],
      data: {
        dateOfArrival: Date.now(),
        primaryKey: "2",
      },
    };
    event.waitUntil(self.registration.showNotification(title, options));
  }
});

self.addEventListener("notificationclick", function (event) {
  event.notification.close();
  event.waitUntil(
    clients.openWindow("https://astra-two-kappa.vercel.app/main")
  );
});
