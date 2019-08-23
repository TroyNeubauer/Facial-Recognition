project "ImGui"
	kind "StaticLib"
	language "C++"
	cppdialect "C++17"
	staticruntime "on"
	systemversion "latest"
	
	targetdir ("bin/" .. outputdir .. "/%{prj.name}")
	objdir ("bin-int/" .. outputdir .. "/%{prj.name}")

	files
	{
		"./*.h",
		"./*.cpp"
	}
	

	filter "configurations:Release"
		runtime "Release"
		optimize "on"
