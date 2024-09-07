import { computed, readonly } from 'vue';

const layoutConfig = useStorage('layout-config', {
  preset: 'Aura',
  primary: 'blue',
  surface: 'slate',
  darkTheme: false,
  menuMode: 'static'
});

const layoutState = useStorage('layout-state', {
  staticMenuDesktopInactive: false,
  overlayMenuActive: false,
  profileSidebarVisible: false,
  configSidebarVisible: false,
  staticMenuMobileActive: false,
  menuHoverActive: false,
  activeMenuItem: null
});

export function useLayout() {
  const setPrimary = (value) => {
    layoutConfig.value.primary = value;
  };

  const setSurface = (value) => {
    layoutConfig.value.surface = value;
  };

  const setPreset = (value) => {
    layoutConfig.value.preset = value;
  };

  const setActiveMenuItem = (item) => {
    layoutState.value.activeMenuItem = item.value || item;
  };

  const setMenuMode = (mode) => {
    layoutConfig.value.menuMode = mode;
  };

  const toggleDarkMode = () => {
    if (!document.startViewTransition) {
      executeDarkModeToggle();
      return;
    }

    document.startViewTransition(() => executeDarkModeToggle());
  };

  const executeDarkModeToggle = () => {
    layoutConfig.value.darkTheme = !layoutConfig.value.darkTheme;
    document.documentElement.classList.toggle('app-dark');
  };

  const onMenuToggle = () => {
    if (layoutConfig.value.menuMode === 'overlay') {
      layoutState.value.overlayMenuActive = !layoutState.value.overlayMenuActive;
    }

    if (window.innerWidth > 991) {
      layoutState.value.staticMenuDesktopInactive = !layoutState.value.staticMenuDesktopInactive;
    } else {
      layoutState.value.staticMenuMobileActive = !layoutState.value.staticMenuMobileActive;
    }
  };

  const resetMenu = () => {
    layoutState.value.overlayMenuActive = false;
    layoutState.value.staticMenuMobileActive = false;
    layoutState.value.menuHoverActive = false;
  };

  const isSidebarActive = computed(() => layoutState.value.overlayMenuActive || layoutState.value.staticMenuMobileActive);

  const isDarkTheme = computed(() => layoutConfig.value.darkTheme);

  const getPrimary = computed(() => layoutConfig.value.primary);

  const getSurface = computed(() => layoutConfig.value.surface);

  return {
    layoutConfig: readonly(layoutConfig.value),
    layoutState: readonly(layoutState.value),
    onMenuToggle,
    isSidebarActive,
    isDarkTheme,
    getPrimary,
    getSurface,
    setActiveMenuItem,
    toggleDarkMode,
    setPrimary,
    setSurface,
    setPreset,
    resetMenu,
    setMenuMode
  };
}
