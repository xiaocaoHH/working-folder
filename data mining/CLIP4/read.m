function [attribute,Data]=read(DatasetName)

fid=fopen(DatasetName);
num=1;
count=1;
while 1
        tline=fgetl(fid);
        if (~ischar(tline))
            break;
        end        
        if(~isempty(tline))            
            index=findstr(tline,','); 
            if num==2
%%       
              %read name of attribute;
              for i=1:length(index)+1                  
                  if i==1
                      attribute(i,:)={tline(1:index(1)-1)};
                  else if i<=length(index)
                          attribute(i,:)={tline(index(i-1)+1:index(i)-1)};
                      else                          
                          attribute(i,:)={tline(index(i-1)+1:end)};
                      end
                  end
              end
%%         
            else if num==3
                    %do nothing;
%%                  
                else if num>3                  
%%             
                        %read data
                        for i=1:length(index)+1                  
                           if i==1
                              Data(count,i)={tline(1:index(1)-1)};
                           else if i<=length(index)                                   
                                   Data(count,i)={tline(index(i-1)+1:index(i)-1)};
                               else
                                   Data(count,i)={tline(index(i-1)+1:end)};
                               end
                           end
                        end                        
                        count=count+1;
                    end
%%  
                end
            end
        end        
        num=num+1;
end
fclose(fid);

