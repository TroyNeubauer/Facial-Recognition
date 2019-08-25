workspace "Facial Recognition"
	architecture "x64"
	startproject "Natives"

	configurations
	{
		"Release",
	}

outputdir = "%{cfg.buildcfg}-%{cfg.system}-%{cfg.architecture}"

-- Include directories relative to root folder (solution directory)
IncludeDir = {}
IncludeDir["Glad"] = "Natives/vendor/Glad/include"
IncludeDir["ImGui"] = "Natives/vendor/imgui"

JDKHome = os.getenv("JDK_HOME")

include "Natives/vendor/Glad"
include "Natives/vendor/imgui"

project "Natives"
	location "Natives"
	kind "SharedLib"
	language "C++"
	cppdialect "C++17"
	staticruntime "on" 
	intrinsics "on"
	systemversion "latest"
	targetname "FR"


	vectorextensions "AVX"

	targetdir ("bin/" .. outputdir .. "/%{prj.name}")
	objdir ("bin-int/" .. outputdir .. "/%{prj.name}")

	files
	{
		"%{prj.name}/src/**.h",
		"%{prj.name}/src/**.cpp",
	}

	includedirs
	{
		"%{prj.name}/src",
		"%{IncludeDir.Glad}",
		"%{IncludeDir.ImGui}",
		JDKHome.."/include",
	}

	links 
	{ 
		"Glad",
		"ImGui",
	}

	defines
	{
		"_CRT_SECURE_NO_WARNINGS"
	}


	filter "system:windows"

		links "Pdh.lib"

		includedirs
		{
			JDKHome.."/include/win32",
		}

		defines
		{

		}


	filter "system:linux"
	
		libdirs
		{
			"/usr/lib/x86_64-linux-gnu/",
		}
	
		links "libX11.a"

		defines
		{
		}

	filter "configurations:Release"
		runtime "Release"
		optimize "speed"
		inlining "auto"
		floatingpoint "Fast"

