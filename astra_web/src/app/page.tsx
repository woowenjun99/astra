"use client";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
  Activity,
  Target,
  Calendar,
  BarChart3,
  Users,
  Smartphone,
  ArrowRight,
  Trophy,
} from "lucide-react";

export default function LandingPage() {
  const features = [
    {
      icon: Activity,
      title: "Smart Workout Tracking",
      description:
        "Log workouts with detailed exercise tracking, sets, reps, and weights. AI-powered suggestions help optimize your routine.",
    },
    {
      icon: Target,
      title: "Goal Setting & Achievement",
      description:
        "Set personalized fitness goals and track your progress with visual charts and milestone celebrations.",
    },
    {
      icon: Calendar,
      title: "Workout Scheduling",
      description:
        "Plan your fitness journey with our intelligent scheduling system that adapts to your lifestyle.",
    },
    {
      icon: BarChart3,
      title: "Advanced Analytics",
      description:
        "Gain insights into your performance with detailed analytics, progress charts, and trend analysis.",
    },
    {
      icon: Users,
      title: "Community Support",
      description:
        "Connect with like-minded fitness enthusiasts, share achievements, and stay motivated together.",
    },
    {
      icon: Smartphone,
      title: "Mobile Optimized",
      description:
        "Access your fitness data anywhere with our responsive design that works perfectly on all devices.",
    },
  ];

  const stats = [
    { number: "1", label: "Active Users" },
    { number: "3", label: "Workouts Tracked" },
  ];

  return (
    <div className="min-h-screen bg-background">
      {/* Navigation */}
      <nav className="border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60 sticky top-0 z-50">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center space-x-2">
              <Activity className="h-8 w-8 text-primary" />
              <span className="text-2xl font-bold text-foreground">Astra</span>
            </div>
            <div className="hidden md:flex items-center space-x-8">
              <a
                href="#features"
                className="text-muted-foreground hover:text-foreground transition-colors"
              >
                Features
              </a>
              <Link href="/auth" passHref>
                <Button>Get Started</Button>
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="py-20 lg:py-32 bg-gradient-to-br from-primary/5 via-background to-secondary/5">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <div className="space-y-8">
              <div className="space-y-4">
                <h1 className="text-4xl lg:text-6xl font-bold tracking-tight">
                  Transform Your
                  <span className="text-primary block">Life</span>
                </h1>
                <p className="text-xl text-muted-foreground max-w-lg">
                  With Astra, you can manage your health, finance and schedule
                  all within a single app.
                </p>
              </div>

              <div className="flex flex-col sm:flex-row gap-4">
                <Link href="/auth">
                  <Button size="lg" className="w-full sm:w-auto">
                    Start Now
                    <ArrowRight className="ml-2 h-4 w-4" />
                  </Button>
                </Link>
              </div>
            </div>

            <div className="relative">
              <div className="bg-card rounded-2xl shadow-2xl p-8 border">
                <div className="space-y-6">
                  <div className="flex items-center justify-between">
                    <h3 className="text-lg font-semibold">
                      {"Today's"} Workout
                    </h3>
                    <Badge variant="secondary">In Progress</Badge>
                  </div>

                  <div className="space-y-4">
                    <div className="flex items-center justify-between p-3 bg-primary/5 rounded-lg">
                      <div className="flex items-center space-x-3">
                        <div className="w-2 h-2 bg-primary rounded-full" />
                        <span className="font-medium">Push-ups</span>
                      </div>
                      <span className="text-sm text-muted-foreground">
                        3 sets × 15 reps
                      </span>
                    </div>

                    <div className="flex items-center justify-between p-3 bg-muted/50 rounded-lg">
                      <div className="flex items-center space-x-3">
                        <div className="w-2 h-2 bg-muted-foreground rounded-full" />
                        <span className="font-medium">Squats</span>
                      </div>
                      <span className="text-sm text-muted-foreground">
                        3 sets × 20 reps
                      </span>
                    </div>

                    <div className="flex items-center justify-between p-3 bg-muted/50 rounded-lg">
                      <div className="flex items-center space-x-3">
                        <div className="w-2 h-2 bg-muted-foreground rounded-full" />
                        <span className="font-medium">Plank</span>
                      </div>
                      <span className="text-sm text-muted-foreground">
                        3 sets × 60s
                      </span>
                    </div>
                  </div>

                  <div className="pt-4 border-t">
                    <div className="flex justify-between text-sm">
                      <span className="text-muted-foreground">Progress</span>
                      <span className="font-medium">1/3 completed</span>
                    </div>
                    <div className="w-full bg-muted rounded-full h-2 mt-2">
                      <div className="bg-primary h-2 rounded-full w-1/3" />
                    </div>
                  </div>
                </div>
              </div>

              {/* Floating stats */}
              <div className="absolute -top-4 -right-4 bg-card rounded-lg shadow-lg p-4 border">
                <div className="flex items-center space-x-2">
                  <Trophy className="h-5 w-5 text-yellow-500" />
                  <div>
                    <div className="text-sm font-semibold">7 Day Streak!</div>
                    <div className="text-xs text-muted-foreground">
                      Keep it up!
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 bg-muted/30">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-2 lg:grid-cols-2 gap-8">
            {stats.map((stat, index) => (
              <div key={index} className="text-center">
                <div className="text-3xl lg:text-4xl font-bold text-primary">
                  {stat.number}
                </div>
                <div className="text-muted-foreground mt-1">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="py-20">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center space-y-4 mb-16">
            <Badge variant="secondary" className="w-fit mx-auto">
              Features
            </Badge>
            <h2 className="text-3xl lg:text-4xl font-bold">
              Everything you need to succeed
            </h2>
            <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
              Powerful features designed to help you track, analyze, and achieve
              your goals faster than ever.
            </p>
          </div>

          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            {features.map((feature, index) => (
              <Card
                key={index}
                className="border-0 shadow-sm hover:shadow-md transition-shadow"
              >
                <CardHeader>
                  <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mb-4">
                    <feature.icon className="h-6 w-6 text-primary" />
                  </div>
                  <CardTitle className="text-xl">{feature.title}</CardTitle>
                </CardHeader>
                <CardContent>
                  <CardDescription className="text-base leading-relaxed">
                    {feature.description}
                  </CardDescription>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="py-12 border-t bg-muted/30">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid md:grid-cols-4 gap-8">
            <div className="space-y-4">
              <div className="flex items-center space-x-2">
                <Activity className="h-6 w-6 text-primary" />
                <span className="text-xl font-bold">Astra</span>
              </div>
              <p className="text-muted-foreground text-sm">
                Transform your fitness journey with intelligent tracking, goal
                setting, and community support.
              </p>
            </div>

            <div>
              <h4 className="font-semibold mb-4">Product</h4>
              <ul className="space-y-2 text-sm text-muted-foreground">
                <li>
                  <a
                    href="#"
                    className="hover:text-foreground transition-colors"
                  >
                    Features
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
}
