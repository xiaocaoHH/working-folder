function [POS_vector,N_now]=EliminateRedundantMatrices(POS_vector)

%get rid of the redundant matrices;
temp_POS_vector=POS_vector(1);
sz_POS_vector=size(POS_vector);

for i=2:sz_POS_vector(2)
    
    sz_temp_POS_vector=size(temp_POS_vector);
    count=0;
    for j=1:sz_temp_POS_vector(2)        
        sz_sample=size(POS_vector{i});
        num=0;
        lable=0;
        
        for m=1:sz_sample(1)
            sign=in(temp_POS_vector{j},POS_vector{i}(m,:));
            if sign==1
               num=num+1; 
            end                
        end
        
        if num==sz_sample(1)
            lable=1;
            break;
        else
            count=count+1;            
        end
        
    end  
    
    if lable==1
    else        
       temp_POS_vector{end+1}=POS_vector{i};
    end

end

POS_vector=temp_POS_vector;
sz=size(POS_vector);
N_now=sz(2);
