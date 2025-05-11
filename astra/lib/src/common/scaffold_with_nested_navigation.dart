import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:getwidget/getwidget.dart';
import 'package:go_router/go_router.dart';

class ScaffoldWithNestedNavigation extends ConsumerWidget {
  const ScaffoldWithNestedNavigation({Key? key, required this.navigationShell})
    : super(key: key ?? const ValueKey('ScaffoldWithNestedNavigation'));
  final StatefulNavigationShell navigationShell;

  void _goBranch(int index) {
    navigationShell.goBranch(
      index,
      initialLocation: index == navigationShell.currentIndex,
    );
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      appBar: GFAppBar(),
      body: SafeArea(
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: 16),
          child: SizedBox(width: double.infinity, child: navigationShell),
        ),
      ),
      bottomNavigationBar: CurvedNavigationBar(
        index: navigationShell.currentIndex,
        items: const [Icon(Icons.home), Icon(Icons.settings)],
        onTap: _goBranch,
      ),
      drawer: GFDrawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            GFDrawerHeader(
              decoration: BoxDecoration(color: Theme.of(context).primaryColor),
            ),
          ],
        ),
      ),
    );
  }
}
