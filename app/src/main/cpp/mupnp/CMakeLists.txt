# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fno-rtti -fno-exceptions -Wall")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -DHAVE_CONFIG_H -DMUPNP_USE_OPENSSL")
set(LINK_STATIC_DIR "${PROJECT_SOURCE_DIR}/../jniStaticLibs/${ANDROID_ABI}")
set(LINK_SHARED_DIR "${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}")


include_directories(${PROJECT_SOURCE_DIR}/mupnp/src ${PROJECT_SOURCE_DIR}/prebuilt/include ${PROJECT_SOURCE_DIR}/prebuilt/include/libxml2 )
#MESSAGE(FATAL_ERROR "This is BINARY PROJECT_SOURCE_DIR=" ${PROJECT_SOURCE_DIR})
add_library( # Sets the name of the library.
             native-mupnp-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
                src/src/mupnp/xml/xml_node_list.c
                src/src/mupnp/xml/xml_parser.c
                src/src/mupnp/xml/xml_parser_expat.c
                src/src/mupnp/xml/xml_node.c
                src/src/mupnp/xml/xml_attribute_list.c
                src/src/mupnp/xml/xml_parser_libxml2.c
                src/src/mupnp/xml/xml_function.c
                src/src/mupnp/xml/xml_attribute.c
                src/src/mupnp/service_list.c
                src/src/mupnp/action_list.c
                src/src/mupnp/argument_list.c
                src/src/mupnp/argument.c
                src/src/mupnp/controlpoint_http_server.c
                src/src/mupnp/icon_list.c
                src/src/mupnp/upnp_status.c
                src/src/mupnp/http/http_response.c
                src/src/mupnp/http/http_packet.c
                src/src/mupnp/http/http_persistent_connection.c
                src/src/mupnp/http/http_server_list.c
                src/src/mupnp/http/http_date.c
                src/src/mupnp/http/http_server.c
                src/src/mupnp/http/http_header_list.c
                src/src/mupnp/http/http_header.c
                src/src/mupnp/http/http.c
                src/src/mupnp/http/http_request.c
                src/src/mupnp/service_notify.c
                src/src/mupnp/servicestate_table.c
                src/src/mupnp/net/datagram_packet.c
                src/src/mupnp/net/net_function.c
                src/src/mupnp/net/interface.c
                src/src/mupnp/net/url.c
                src/src/mupnp/net/uri.c
                src/src/mupnp/net/interface_function.c
                src/src/mupnp/net/socket.c
                src/src/mupnp/net/interface_list.c
                src/src/mupnp/net/socket_list.c
                src/src/mupnp/upnp_function.c
                src/src/mupnp/device.c
                src/src/mupnp/statevariable.c
                src/src/mupnp/controlpoint.c
                src/src/mupnp/device_list.c
                src/src/mupnp/io/file_list.c
                src/src/mupnp/io/file.c
                src/src/mupnp/action.c
                src/src/mupnp/ssdp/ssdp.c
                src/src/mupnp/ssdp/ssdp_server.c
                src/src/mupnp/ssdp/ssdp_request.c
                src/src/mupnp/ssdp/ssdp_response.c
                src/src/mupnp/ssdp/ssdp_response_server.c
                src/src/mupnp/ssdp/httpmu_socket.c
                src/src/mupnp/ssdp/ssdp_socket.c
                src/src/mupnp/ssdp/ssdp_server_list.c
                src/src/mupnp/ssdp/ssdp_response_server_list.c
                src/src/mupnp/ssdp/httpu_socket.c
                src/src/mupnp/ssdp/ssdp_packet.c
                src/src/mupnp/service_ssdp_server.c
                src/src/mupnp/device_advertiser.c
                src/src/mupnp/control/action_request.c
                src/src/mupnp/control/query_request.c
                src/src/mupnp/control/query_response.c
                src/src/mupnp/control/action_ctrl.c
                src/src/mupnp/control/query_ctrl.c
                src/src/mupnp/control/control_function.c
                src/src/mupnp/control/action_response.c
                src/src/mupnp/event/eventlistener_list.c
                src/src/mupnp/event/subscriber_list.c
                src/src/mupnp/event/subscriber.c
                src/src/mupnp/event/subscription_request.c
                src/src/mupnp/event/notify_request.c
                src/src/mupnp/event/subscription_response.c
                src/src/mupnp/event/subscription.c
                src/src/mupnp/event/property_list.c
                src/src/mupnp/event/property.c
                src/src/mupnp/device_http_server.c
                src/src/mupnp/service_std_dcp.c
                src/src/mupnp/soap/soap_request.c
                src/src/mupnp/soap/soap.c
                src/src/mupnp/soap/soap_response.c
                src/src/mupnp/util/thread.c
                src/src/mupnp/util/thread_list.c
                src/src/mupnp/util/string_tokenizer.c
                src/src/mupnp/util/mutex.c
                src/src/mupnp/util/list.c
                src/src/mupnp/util/time_wince.c
                src/src/mupnp/util/dictionary_elem.c
                src/src/mupnp/util/dictionary.c
                src/src/mupnp/util/string_function.c
                src/src/mupnp/util/log.c
                src/src/mupnp/util/cond.c
                src/src/mupnp/util/time.c
                src/src/mupnp/util/string.c
                src/src/mupnp/device_ssdp_server.c
                src/src/mupnp/service.c
                src/src/mupnp/icon.c
                src/src/mupnp/controlpoint_event.c
             )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.


add_library( crypto-lib
           STATIC
           IMPORTED )

set_target_properties( # Specifies the target library.
                     crypto-lib

                     # Specifies the parameter you want to define.
                     PROPERTIES IMPORTED_LOCATION

                     # Provides the path to the library you want to import.
                     ${LINK_STATIC_DIR}/libcrypto.a )



add_library( ssl-lib
           STATIC
           IMPORTED )

set_target_properties( # Specifies the target library.
                     ssl-lib

                     # Specifies the parameter you want to define.
                     PROPERTIES IMPORTED_LOCATION

                     # Provides the path to the library you want to import.
                     ${LINK_STATIC_DIR}/libssl.a )

add_library( iconv-lib
           SHARED
           IMPORTED )

set_target_properties( # Specifies the target library.
                     iconv-lib

                     # Specifies the parameter you want to define.
                     PROPERTIES IMPORTED_LOCATION

                     # Provides the path to the library you want to import.
                     ${LINK_SHARED_DIR}/libiconv.so )

add_library( xml2-lib
           SHARED
           IMPORTED )

set_target_properties( # Specifies the target library.
                     xml2-lib

                     # Specifies the parameter you want to define.
                     PROPERTIES IMPORTED_LOCATION

                     # Provides the path to the library you want to import.
                     ${LINK_SHARED_DIR}/libxml2.so )


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

find_library( # Sets the name of the path variable.
            OpenSLES-lib

            # Specifies the name of the NDK library that
            # you want CMake to locate.
            OpenSLES )

#----------------------------------------------------------------

#-----------------------------------------------------------------
# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.


target_link_libraries( # Specifies the target library.
                       native-mupnp-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       android
                       ${log-lib}
                       ${OpenSLES-lib}
                       ssl-lib
                       crypto-lib
                       xml2-lib
                       iconv-lib
                       )